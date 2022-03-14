package ec.edu.insteclrg.view.bean;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.PrimeFaces;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	private List<TestIdDTO> registros;

	private TestIdDTO selectedProduct;

	private List<TestIdDTO> selectedProducts;

	@PostConstruct
	public void init() {
		testIdDto = new TestIdDTO();
		handler = new TestIdApiHandler();
		cargarTodosRegistros();
	}

	private void cargarTodosRegistros() {
		ApiResponseDTO<List<TestIdDTO>> response = handler.buscarTodo();
		ObjectMapper mapper = new ObjectMapper();
		if (response.isSuccess())
			this.registros = mapper.convertValue(response.getResult(), new TypeReference<List<TestIdDTO>>() {
			});

	}

	public void openNew() {
		this.selectedProduct = new TestIdDTO();
	}

	public void saveProduct() {
		if (this.selectedProduct.getId() == 0) {
			ApiResponseDTO<TestIdDTO> response = handler.guardar(selectedProduct);
			if (response.isSuccess()) {
				cargarTodosRegistros();
				Mensajes.addMsg(MensajesTipo.INFORMACION, "Guardado correctamente");
			} else
				Mensajes.addMsg(MensajesTipo.ERROR, "Error: no se pudo guardar");
		} else {
			ApiResponseDTO<TestIdDTO> response = handler.actualizar(selectedProduct);
			if (response.isSuccess()) {
				Mensajes.addMsg(MensajesTipo.INFORMACION, "Actualizado correctamente");
			} else
				Mensajes.addMsg(MensajesTipo.ERROR, "Error: no se pudo actualizar");
		}
		PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
		PrimeFaces.current().ajax().update("frm:growl", "frm:dt-products");
	}

	public void deleteProduct() {
		// TODO
		// Codigo para eliminar
		this.registros.remove(this.selectedProduct);
		this.selectedProduct = null;
		Mensajes.addMsg(MensajesTipo.INFORMACION, "Eliminado correctamente");
		PrimeFaces.current().ajax().update("frm:growl", "frm:dt-products");
	}

	public void deleteSelectedProducts() {
		// TODO
		// Codigo para eliminar lo seleccionado
		this.registros.removeAll(this.selectedProducts);
		this.selectedProducts = null;
		Mensajes.addMsg(MensajesTipo.INFORMACION, "Registros eliminados correctamente");
		PrimeFaces.current().ajax().update("frm:growl", "frm:dt-products");
		PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
	}

	public String getDeleteButtonMessage() {
		if (hasSelectedProducts()) {
			int size = this.selectedProducts.size();
			return size > 1 ? size + " registros seleccionados" : "1 registro seleccionado";
		}
		return "Eliminar";
	}

	public boolean hasSelectedProducts() {
		return this.selectedProducts != null && !this.selectedProducts.isEmpty();
	}
}
