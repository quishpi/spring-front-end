package ec.edu.insteclrg.handler.remote;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;

import ec.edu.insteclrg.dto.ApiResponseDTO;
import ec.edu.insteclrg.dto.TestIdDTO;
import ec.edu.insteclrg.handler.error.ErrorJsonHandler;

public class TestIdApiHandler extends ApiHandler<TestIdDTO> {

	// TODO
	// Refactorizar
	
	private static final long serialVersionUID = 1L;

	public ApiResponseDTO<TestIdDTO> buscarTestId(Long id) {
		TestIdDTO dto = new TestIdDTO();
		String remotePath = "http://localhost:8080/api/v1.0/testid/" + id + "/buscar";
		String responseJsonStr = super.consumeREST(dto, HttpMethod.GET, remotePath);
		ErrorJsonHandler<TestIdDTO> jsonHandler = new ErrorJsonHandler<TestIdDTO>(dto);
		ApiResponseDTO<TestIdDTO> response = jsonHandler.getServerResponse(responseJsonStr);
		return response;

	}

	public ApiResponseDTO<List<TestIdDTO>> buscarTodo() {
		List<TestIdDTO> dto = new ArrayList<>();
		String remotePath = "http://localhost:8080/api/v1.0/testid/listar";
		String responseJsonStr = super.consumeREST(dto, HttpMethod.GET, remotePath);
		ErrorJsonHandler<List<TestIdDTO>> jsonHandler = new ErrorJsonHandler<List<TestIdDTO>>(dto);
		ApiResponseDTO<List<TestIdDTO>> response = jsonHandler.getServerResponse(responseJsonStr);
		return response;

	}

	public ApiResponseDTO<TestIdDTO> guardar(TestIdDTO dto) {
		String remotePath = "http://localhost:8080/api/v1.0/testid/guardar";
		String responseJsonStr = super.consumeREST(dto, HttpMethod.POST, remotePath);
		ErrorJsonHandler<TestIdDTO> jsonHandler = new ErrorJsonHandler<TestIdDTO>(dto);
		ApiResponseDTO<TestIdDTO> response = jsonHandler.getServerResponse(responseJsonStr);
		return response;

	}

	public ApiResponseDTO<TestIdDTO> actualizar(TestIdDTO dto) {
		String remotePath = "http://localhost:8080/api/v1.0/testid/actualizar";
		String responseJsonStr = super.consumeREST(dto, HttpMethod.PUT, remotePath);
		ErrorJsonHandler<TestIdDTO> jsonHandler = new ErrorJsonHandler<TestIdDTO>(dto);
		ApiResponseDTO<TestIdDTO> response = jsonHandler.getServerResponse(responseJsonStr);
		return response;

	}
}
