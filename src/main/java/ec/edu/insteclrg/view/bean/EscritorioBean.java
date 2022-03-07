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
public class EscritorioBean {

	private String texto;

	private long id;

	private TestIdApiHandler handler;

	@PostConstruct
	public void init() {
		texto = "IST Luis Rogerio González";
		handler = new TestIdApiHandler();
	}

	public void save() {
		ApiResponseDTO<TestIdDTO> result = handler.buscarTestId(id);
		if (result.isSuccess())
			Mensajes.addMsg(MensajesTipo.INFORMACION, "Recuperado correctamente: " + result.getResult().getNombre());
		else
			Mensajes.addMsg(MensajesTipo.ERROR, "Error: " + result.getError());
		PrimeFaces.current().ajax().update("frm:growl");
	}

}
