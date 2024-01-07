package pl.latusikl.demo.nativeimage.library.controler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.latusikl.demo.nativeimage.library.dto.MemberDto;
import pl.latusikl.demo.nativeimage.library.entity.Member;
import pl.latusikl.demo.nativeimage.exception.BadRequestException;
import pl.latusikl.demo.nativeimage.library.service.MemberService;

@RestController
@RequestMapping("/members")
public class MemberController extends AbstractCRUDController<Member, MemberDto>
{

	private final MemberService memberService;

	protected MemberController(final MemberService memberService)
	{
		super(memberService);
		this.memberService = memberService;
	}

	@Override
	protected void throwValidationErrorBeforeCreationIfNeeded(final MemberDto dto)
	{
		if(memberService.existByEmail(dto.getEmail())){
			throw new BadRequestException("User with provided email already exist");
		}
	}
}
