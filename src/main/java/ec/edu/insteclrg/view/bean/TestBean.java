package ec.edu.insteclrg.view.bean;

import javax.annotation.PostConstruct;

import org.primefaces.PrimeFaces;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.edu.insteclrg.common.enums.MensajesTipo;
import ec.edu.insteclrg.dto.ApiResponseDTO;
import ec.edu.insteclrg.dto.TestIdDTO;
import ec.edu.insteclrg.handler.remote.TestIdApiHandler;
import ec.edu.insteclrg.utils.Mensajes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("view")
public class TestBean {

	private long id;

	private TestIdDTO testIdDto;

	private TestIdApiHandler handler;

	@PostConstruct
	public void init() {
		testIdDto = new TestIdDTO();
		handler = new TestIdApiHandler();
	}

	public void save() {
		ApiResponseDTO<TestIdDTO> result = handler.buscarTestId(testIdDto.getId());
		if (result.isSuccess()) {
			testIdDto = result.getResult();
			Mensajes.addMsg(MensajesTipo.INFORMACION, "Recuperado correctamente: " + result.getResult().getNombre());
		} else {
			testIdDto.setNombre("");
			Mensajes.addMsg(MensajesTipo.ERROR, "Error: " + result.getError());
		}
		PrimeFaces.current().ajax().update("frm", "frm:growl");
	}

}
