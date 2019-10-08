package com.game.itstar.dialect;

import org.hibernate.dialect.identity.IdentityColumnSupportImpl;


/**
 * @Author 朱斌
 * @Date 2019/9/24  10:23
 * @Desc
 */
public class SQLiteDialectIdentityColumnSupport extends IdentityColumnSupportImpl {
    public SQLiteDialectIdentityColumnSupport() {
        super();
    }

    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

  /*
	public boolean supportsInsertSelectIdentity() {
    return true; // As specified in NHibernate dialect
  }
  */

    @Override
    public boolean hasDataTypeInIdentityColumn() {
        // As specified in NHibernate dialect
        // FIXME true
        return false;
    }

  /*
	public String appendIdentitySelectToInsert(String insertString) {
    return new StringBuffer(insertString.length()+30). // As specified in NHibernate dialect
      append(insertString).
      append("; ").append(getIdentitySelectString()).
      toString();
  }
  */

    @Override
    public String getIdentitySelectString(String table, String column, int type) {
        return "select last_insert_rowid()";
    }

    @Override
    public String getIdentityColumnString(int type) {
        // return "integer primary key autoincrement";
        // FIXME "autoincrement"
        return "integer";
    }
}
