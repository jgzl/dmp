package cn.cleanarch.dmp.module.system.dal.mysql.dict;

import cn.cleanarch.dmp.framework.common.pojo.PageResult;
import cn.cleanarch.dmp.framework.mybatis.core.mapper.BaseMapperX;
import cn.cleanarch.dmp.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.cleanarch.dmp.module.system.controller.admin.dict.vo.data.DictDataExportReqVO;
import cn.cleanarch.dmp.module.system.controller.admin.dict.vo.data.DictDataPageReqVO;
import cn.cleanarch.dmp.module.system.dal.dataobject.dict.DictDataDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Mapper
public interface DictDataMapper extends BaseMapperX<DictDataDO> {

    default DictDataDO selectByDictTypeAndValue(String dictType, String value) {
        return selectOne(new LambdaQueryWrapper<DictDataDO>().eq(DictDataDO::getDictType, dictType)
                .eq(DictDataDO::getValue, value));
    }

    default DictDataDO selectByDictTypeAndLabel(String dictType, String label) {
        return selectOne(new LambdaQueryWrapper<DictDataDO>().eq(DictDataDO::getDictType, dictType)
                .eq(DictDataDO::getLabel, label));
    }

    default List<DictDataDO> selectByDictTypeAndValues(String dictType, Collection<String> values) {
        return selectList(new LambdaQueryWrapper<DictDataDO>().eq(DictDataDO::getDictType, dictType)
                .in(DictDataDO::getValue, values));
    }

    default long selectCountByDictType(String dictType) {
        return selectCount(DictDataDO::getDictType, dictType);
    }

    default PageResult<DictDataDO> selectPage(DictDataPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DictDataDO>()
                .likeIfPresent(DictDataDO::getLabel, reqVO.getLabel())
                .likeIfPresent(DictDataDO::getDictType, reqVO.getDictType())
                .eqIfPresent(DictDataDO::getStatus, reqVO.getStatus())
                .orderByDesc(Arrays.asList(DictDataDO::getDictType, DictDataDO::getSort)));
    }

    default List<DictDataDO> selectList(DictDataExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DictDataDO>().likeIfPresent(DictDataDO::getLabel, reqVO.getLabel())
                .likeIfPresent(DictDataDO::getDictType, reqVO.getDictType())
                .eqIfPresent(DictDataDO::getStatus, reqVO.getStatus()));
    }

}
