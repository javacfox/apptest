package com.game.itstar.service.serviceImpl;

import com.game.itstar.base.repository.CommonRepository;
import com.game.itstar.entity.Team;
import com.game.itstar.entity.User;
import com.game.itstar.entity.UserTeam;
import com.game.itstar.enums.TeamType;
import com.game.itstar.qycode.QrCodeUtils;
import com.game.itstar.repository.TeamRepository;
import com.game.itstar.repository.UserTeamRepository;
import com.game.itstar.response.ResException;
import com.game.itstar.service.TeamService;
import com.game.itstar.utile.ConstantProperties;
import com.game.itstar.utile.DateExtendUtil;
import com.game.itstar.utile.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PrimitiveIterator;

/**
 * @Author 朱斌
 * @Date 2019/9/29  14:48
 * @Desc 战队
 */
@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserTeamRepository userTeamRepository;
    @Autowired
    private CommonRepository commonRepository;
    @Value("${file.save-path}")
    private String baseFilePath;

    /**
     * 创建新战队
     *
     * @param team
     * @param request
     * @return
     */
    @Override
    public Team create(Team team, HttpServletRequest request) {
        team.setCount(1);
        teamRepository.save(team);

        // 1.从session中回去用户信息
        User user = userService.getAuthUser(request);
        if (user == null) {
            throw new ResException("该用户还未登录,请先登录!");
        }

        // 2.绑定关联关系
        UserTeam userTeam = new UserTeam();
        userTeam.setTeamId(team.getId());
        userTeam.setUserId(user.getId());
        userTeam.setType(TeamType.CAPTAIN.getValue());
        userTeamRepository.save(userTeam);
        return team;
    }

    /**
     * 生成二维码
     *
     * @param team
     * @param type 1-不需要审核 2-需要审核
     * @return
     */
    public Team createQRcode(Team team, Integer type, HttpServletResponse response) {
        BufferedImage qRImageWithLogo = null;
        try {
            String logoPath = null;

            // 是否调用服务器图片
            if (Helpers.isNotNullAndEmpty(team.getTeamPic())) {
                // logo图片地址
                logoPath = baseFilePath + "/" + team.getTeamPic();
                team.setIsEffective(new File(logoPath).exists());
            } else {
                team.setIsEffective(false);
            }

            // 不需要审核
            if (type.equals(ConstantProperties.NO_VALIDATION_REQUIRED)) {
                String contents = "欢迎加入" + team.getTeamName() + "战队";
                qRImageWithLogo = QrCodeUtils.createImage(contents, logoPath, true);

            } else if (type.equals(ConstantProperties.VALIDATION_REQUIRED)) {//需要审核
                String contents = "欢迎加入" + team.getTeamName() + "战队, " + "请耐心等候审核!";
                qRImageWithLogo = QrCodeUtils.createImage(contents, logoPath, true);
            }

            // 把二维码图片上传到临时文件夹
            String uploadPath = team.getTeamName();

            //文件路径不存在,则新建
            String checkPath = baseFilePath + "/" + uploadPath;
            File fileStream = new File(checkPath);
            if (!fileStream.exists()) {
                fileStream.mkdirs();
            }

            if (qRImageWithLogo == null) {
                throw new ResException("上传二维码不能为空");
            }
            String relatedPath = "/" + qRImageWithLogo;
            String realPath = uploadPath + relatedPath;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qRImageWithLogo, "jpg", baos);

            byte[] QRJPG = baos.toByteArray();
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");

            ServletOutputStream os = response.getOutputStream();
            os.write(QRJPG); // 自此完成一套，图片读入，写入流，转为字节数组，写入输出流
            os.flush();
            os.close();
            baos.close();

            team.setTeamCode(realPath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResException("生成二维码失败 => " + e.getMessage());
        }
       return commonRepository.merge(team);
    }

}
