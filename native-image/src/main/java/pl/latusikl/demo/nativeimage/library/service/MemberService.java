package pl.latusikl.demo.nativeimage.library.service;

import java.util.UUID;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.latusikl.demo.nativeimage.library.dto.ItemStockDto;
import pl.latusikl.demo.nativeimage.library.dto.MemberDto;
import pl.latusikl.demo.nativeimage.library.entity.ItemStock;
import pl.latusikl.demo.nativeimage.library.entity.Member;
import pl.latusikl.demo.nativeimage.library.service.repository.MemberRepository;

@Service
public class MemberService extends AbstractEntityService<Member, MemberDto>
{
	private final MemberRepository memberRepository;

	protected MemberService(final MemberRepository memberRepository,
	                        final ConversionService conversionService)
	{
		super(memberRepository, conversionService, MemberDto.class, Member.class);
		this.memberRepository = memberRepository;
	}

	public boolean existByEmail(String email)
	{
		return memberRepository.existsByEmail(email);
	}
}
