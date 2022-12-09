package cn.cleanarch.dmp.module.system.api.dict;

import cn.cleanarch.dmp.module.system.api.dict.dto.DictDataRespDTO;
import cn.cleanarch.dmp.module.system.convert.dict.DictDataConvert;
import cn.cleanarch.dmp.module.system.dal.dataobject.dict.DictDataDO;
import cn.cleanarch.dmp.module.system.service.dict.DictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 字典数据 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class DictDataApiImpl implements DictDataApi {

    @Resource
    private DictDataService dictDataService;

    @Override
    public void validDictDatas(String dictType, Collection<String> values) {
        dictDataService.validDictDatas(dictType, values);
    }

    @Override
    public DictDataRespDTO getDictData(String dictType, String value) {
        DictDataDO dictData = dictDataService.getDictData(dictType, value);
        return DictDataConvert.INSTANCE.convert02(dictData);
    }

    @Override
    public DictDataRespDTO parseDictData(String dictType, String label) {
        DictDataDO dictData = dictDataService.parseDictData(dictType, label);
        return DictDataConvert.INSTANCE.convert02(dictData);
    }

}
