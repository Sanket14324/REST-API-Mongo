package com.backend.mongo.restapi.controller;

import com.backend.mongo.restapi.model.Employee;
import com.backend.mongo.restapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("ems/mongo")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<?> getAllEmployees(){
        List<Employee> employeeList = employeeRepository.findAll();
        if(employeeList.size() != 0){
            return new ResponseEntity<List>(employeeList, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Empty collection", HttpStatus.NOT_FOUND);
        }

    }

//    @PostMapping("/login")
//    public String loginEmployee(@RequestBody Employee employeeCredentials){
//        List<Employee> employeeList = employeeRepository.findAll().stream().filter(employee -> Objects.equals(employee.getEmail(), employeeCredentials.getEmail()))
//                .collect(Collectors.toList());
//
//        if(employeeList.size() != 0){
//            if(employeeList.get(0).getPassword().equals(employeeCredentials.getPassword())){
//                return employeeList.get(0).getRole();
////                return jwtService.generateToken(employeeCredentials.getEmail());
//            }
//        }
//        else {
//            return "not found";
//        }
//
//        return "not found";
//
//    }
    @GetMapping("{name}")
    public List<Employee> getEmployeByName(@PathVariable String name){
        return employeeRepository.findAll().stream().filter(employee -> Objects.equals(employee.getName(), name))
                .collect(Collectors.toList());

    }

//    @GetMapping("/user")
//    public String getEmployeeDetails(@PathVariable String token){
//        String useremail = jwtService.extractUsername(token);
//        String userDetails = "";
//
//        List<Employee> employeeList = employeeRepository.findAll().stream().filter(employee -> Objects.equals(employee.getEmail(), useremail))
//                .collect(Collectors.toList());
//
//        userDetails = "{\"name\":\""+employeeList.get(0).getName()+"\", \"role\":\""+employeeList.get(0).getRole()+"\"}";
//
//        return userDetails;
//    }

    @PostMapping()
    public String addNewEmployee(@RequestBody Employee employee){
        employeeRepository.save(employee);
        return "Welcome, you are added into Iauro Humans";
    }

    @DeleteMapping("{Id}")
    public List<Employee> deleteEmployee(@PathVariable String Id){
        Employee employee = employeeRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employeeRepository.delete(employee);
        return employeeRepository.findAll();
    }


    @PutMapping("{id}")
    public String updateEmployeeById(@PathVariable String id, @RequestBody Employee employeeDetails ){
        Employee updateEmployee = employeeRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Customer not exist with id:"+id));

        updateEmployee.setName(employeeDetails.getName());
        updateEmployee.setEmail(employeeDetails.getEmail());

        employeeRepository.save(updateEmployee);
        return "Information updated successfully!!!";
    }

}
