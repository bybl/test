package net.huadong.mapper;

import net.huadong.entity.SysLog;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

public interface SysLogMapper extends Mapper<SysLog> {
    List<HashMap> getLog(HashMap params);
}