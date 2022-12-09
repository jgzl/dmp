package cn.cleanarch.dmp.module.infra.convert.file;

import cn.cleanarch.dmp.framework.common.pojo.PageResult;
import cn.cleanarch.dmp.module.infra.controller.admin.file.vo.file.FileRespVO;
import cn.cleanarch.dmp.module.infra.dal.dataobject.file.FileDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileConvert {

    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    FileRespVO convert(FileDO bean);

    PageResult<FileRespVO> convertPage(PageResult<FileDO> page);

}
