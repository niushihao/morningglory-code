package com.mg.component.sequence;

import java.sql.SQLException;

/**
 * @author qianniu
 * @date 2022/8/15
 * @desc
 */
public interface mgSequenceDao {

    MgSequenceRange nextRange(String name) throws SQLException;

}
