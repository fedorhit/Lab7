package SpringBootMVCREST.controllers;

import SpringBootMVCREST.DTO.PersonDTO;
import SpringBootMVCREST.models.Person;
import SpringBootMVCREST.services.PeopleService;
import SpringBootMVCREST.utils.exceptions.PersonErrorResponse;
import SpringBootMVCREST.utils.exceptions.PersonNotCreatedException;
import SpringBootMVCREST.utils.exceptions.PersonNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<PersonDTO> findAllPerson() {
        return peopleService.findAll().stream()
                .map(this::convertToPersonDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO findPersonById(@PathVariable("id") int id) {
        return convertToPersonDTO(peopleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createPerson(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors) {
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }

            throw new PersonNotCreatedException(errorMsg.toString());
        }

        peopleService.create(convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/up/{id}")
    public ResponseEntity<HttpStatus> updatePerson(@RequestBody PersonDTO person, @PathVariable("id") int id) {
        peopleService.update(convertToPerson(person), id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable("id") int id) {
        peopleService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Person not found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }
}
