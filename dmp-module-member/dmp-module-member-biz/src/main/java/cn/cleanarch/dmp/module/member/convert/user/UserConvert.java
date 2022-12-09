package cn.cleanarch.dmp.module.member.convert.user;

import cn.cleanarch.dmp.module.member.api.user.dto.UserRespDTO;
import cn.cleanarch.dmp.module.member.controller.app.user.vo.AppUserInfoRespVO;
import cn.cleanarch.dmp.module.member.dal.dataobject.user.MemberUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    AppUserInfoRespVO convert(MemberUserDO bean);

    UserRespDTO convert2(MemberUserDO bean);

    List<UserRespDTO> convertList2(List<MemberUserDO> list);

}
