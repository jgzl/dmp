package cn.cleanarch.dmp.module.bpm.dal.mysql.definition;

import cn.cleanarch.dmp.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.cleanarch.dmp.module.bpm.dal.dataobject.definition.BpmProcessDefinitionExtDO;
import cn.cleanarch.dmp.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface BpmProcessDefinitionExtMapper extends BaseMapperX<BpmProcessDefinitionExtDO> {

    default List<BpmProcessDefinitionExtDO> selectListByProcessDefinitionIds(Collection<String> processDefinitionIds) {
        return selectList("process_definition_id", processDefinitionIds);
    }

    default BpmProcessDefinitionExtDO selectByProcessDefinitionId(String processDefinitionId) {
        return selectOne("process_definition_id", processDefinitionId);
    }

}
