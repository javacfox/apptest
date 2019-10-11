package com.game.itstar.service.serviceImpl;

import com.game.itstar.base.repository.CommonRepository;
import com.game.itstar.base.util.PageBean;
import com.game.itstar.criteria.AdmissionCriteria;
import com.game.itstar.entity.*;
import com.game.itstar.enums.ApplyStatusType;
import com.game.itstar.enums.TeamType;
import com.game.itstar.repository.AdmissionRepository;
import com.game.itstar.repository.TeamRepository;
import com.game.itstar.repository.UserTeamRepository;
import com.game.itstar.response.ResException;
import com.game.itstar.service.AdmissionService;
import com.game.itstar.utile.ConstantProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author 朱斌
 * @Date 2019/10/11  13:13
 * @Desc
 */
@Service
public class AdmissionServiceImpl implements AdmissionService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private AdmissionRepository admissionRepository;
    @Autowired
    private UserTeamRepository userTeamRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CommonRepository commonRepository;

    @Override
    public void findByPage(PageBean pageBean, AdmissionCriteria admissionCriteria, HttpServletRequest request) {
        User user = userService.getAuthUser(request);
        if (user == null) {
            throw new ResException("该用户未登陆,请先登录");
        }

        pageBean.setEntityName("Admission a");
        pageBean.setSelect("select a");

        pageBean.setRowsPerPage(Integer.MAX_VALUE);
        commonRepository.findByPage(pageBean, admissionCriteria);
        List<Admission> data = pageBean.getData();

        List<Integer> teamIds = data.stream().map(Admission::getTeamId).collect(Collectors.toList());
        List<Team> teamList = teamRepository.findAllByIdIn(teamIds);

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        for (Admission admission : data) {
            map = new HashMap<>();

            map.put("teamName", teamList.stream().filter(x -> x.getId().equals(admission.getTeamId()))
                    .map(Team::getTeamName).findFirst().orElse(""));
            map.put("operatedAt", admission.getOperatedAt());
            map.put("id", admission.getId());
            map.put("status", admission.getStatus());
            list.add(map);
        }
        pageBean.setData(list);
    }

    /**
     * 申请加入战队
     *
     * @param admission
     * @param request
     * @return
     */
    @Override
    public Admission applyTeam(Admission admission, HttpServletRequest request) {
        Team team = teamRepository.findByTeamCode(admission.getTeamCode());

        // 获取战队邀请码前5位
        String agoCode = admission.getTeamCode().substring(0, 5);
        if (team == null) {
            throw new ResException("未能找到对应的战队,请确认输入的战队代码是否正确!");
        }
        Integer teamId = team.getId();
        User user = userService.getAuthUser(request);
        if (user == null) {
            throw new ResException("该用户未登陆,请先登录");
        }

        boolean var0 = userTeamRepository.existsByTeamIdAndUserId(teamId, user.getId());
        boolean var1 = admissionRepository.existsByTeamIdAndUserId(teamId, user.getId());
        if (var0) {
            throw new ResException("您已经加入该战队,请确认!");

        } else if (var1) {
            throw new ResException("您已经提交申请,请耐心等待!");

        } else if (agoCode.equals(ConstantProperties.TEAM_AUDIT_CODE_AGO)) { //需要审核
            admission.setStatus(ApplyStatusType.APPLY.getValue());
            admission.setMessage("成功申请,请等待队长审批!");
            admission.setTeamId(teamId);
            admission.setUserId(user.getId());
            admissionRepository.save(admission);
            return admission;

        } else {
            admission.setStatus(ApplyStatusType.THROUGH.getValue());
            admission.setMessage("加入战队成功!");
            admission.setTeamId(teamId);
            admission.setUserId(user.getId());
            admissionRepository.save(admission);

            // 建立用户与战队关联关系
            UserTeam userTeam = new UserTeam();
            userTeam.setType(TeamType.PLAYERS.getValue());
            userTeam.setUserId(user.getId());
            userTeam.setTeamId(teamId);
            userTeamRepository.save(userTeam);
        }

        return admission;
    }

    @Override
    @Transactional
    public Admission auditTeam(Integer id, Admission admission, HttpServletRequest request) {
        admission.setId(id);

        User user = userService.getAuthUser(request);
        if (user == null) {
            throw new ResException("该用户未登陆,请先登录");
        }

        UserTeam userTeam = userTeamRepository.findByUserIdAndTeamId(user.getId(), admission.getTeamId());
        if (userTeam == null || userTeam.getType().equals(TeamType.PLAYERS.getValue())) {
            throw new ResException("该用户不是队长,没有权限审核!");
        }

        if (admission.getStatus().equals(ApplyStatusType.REFUSED.getValue())) {
            admission.setStatus(ApplyStatusType.REFUSED.getValue());
            admission.setMessage("拒绝申请,请联系管理员!");
            admission.setTeamCode("!");// 不能为空

        } else {
            admission.setStatus(ApplyStatusType.THROUGH.getValue());
            admission.setMessage("申请通过!");
            admission.setTeamCode("!");//不能为空
            //添加日志
        }
        commonRepository.merge(admission);

        // 建立用户与战队关联关系
        UserTeam userTeam1 = new UserTeam();
        userTeam.setType(TeamType.PLAYERS.getValue());
        userTeam.setUserId(admission.getUserId());
        userTeam.setTeamId(admission.getTeamId());
        userTeamRepository.save(userTeam1);

        return admission;
    }

}
