package io.github.picodotdev.blogbitix.javaswagger;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "message", description = "the message API")
@RequestMapping("/message")
public interface RestApi {

	@Operation(summary = "Get all messages", description = "Returns all messages")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Message.class))) })
	@GetMapping(value = "/", produces = { "application/json" })
	ResponseEntity<List<Message>> getAll();

	@Operation(summary = "Get a message by id", description = "Return a message")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Message.class))),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied"),
			@ApiResponse(responseCode = "404", description = "Message not found") })
	@GetMapping(value = "/{id}", produces = { "application/json" })
	ResponseEntity<Message> getById(@Parameter(description = "Id of message to return", required = true) @PathVariable("id") Long id);

	@Operation(summary = "Adds a message", description = "Add a message")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "400", description = "Invalid data"),
			@ApiResponse(responseCode = "409", description = "Already exists") })
	@PutMapping(value = "/", produces = { "application/json" })
	ResponseEntity<Void> add(@Parameter(description = "Id of message to return", required = true) @RequestBody Message message);

	@Operation(summary = "Deletes a message by id", description = "Delete a message")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied"),
			@ApiResponse(responseCode = "404", description = "Message not found") })
	@DeleteMapping(value = "/{id}", produces = { "application/json" })
	ResponseEntity<Void> deleteBydId(@Parameter(description = "Id of message to delete", required = true) @PathVariable("id") Long id);
}
