package pl.latusikl.demo.nativeimage.library.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import pl.latusikl.demo.nativeimage.library.dto.MemberDto;
import pl.latusikl.demo.nativeimage.library.entity.Member;

@Component
public class MemberDtoToMemberConverter implements Converter<MemberDto, Member>
{

	@Override
	public Member convert(final MemberDto source)
	{
		final var member = new Member();
		member.setName(source.getName());
		member.setSurname(source.getSurname());
		member.setEmail(source.getEmail());
		member.setIdCardKey(source.getIdCardKey());
		return member;
	}
}
