package pl.latusikl.demo.app.library.controler;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.latusikl.demo.app.exception.BadRequestException;
import pl.latusikl.demo.app.library.dto.MemberDto;
import pl.latusikl.demo.app.library.entity.Member;
import pl.latusikl.demo.app.library.service.MemberService;

@RestController
@RequestMapping("/members")
@RegisterReflectionForBinding(MemberDto.class)
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
		if (memberService.existByEmail(dto.getEmail()))
		{
			throw new BadRequestException("User with provided email already exist");
		}
	}
}
