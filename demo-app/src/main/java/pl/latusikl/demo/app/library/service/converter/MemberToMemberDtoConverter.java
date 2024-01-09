package pl.latusikl.demo.app.library.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import pl.latusikl.demo.app.library.dto.MemberDto;
import pl.latusikl.demo.app.library.entity.Member;

@Component
public class MemberToMemberDtoConverter implements Converter<Member, MemberDto>
{

	@Override
	public MemberDto convert(final Member source)
	{
		return MemberDto.builder()
		                .name(source.getName())
		                .surname(source.getSurname())
		                .email(source.getEmail())
		                .idCardKey(source.getIdCardKey())
		                .build();
	}
}
