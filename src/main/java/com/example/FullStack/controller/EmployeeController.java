package com.example.FullStack.controller;

import com.example.FullStack.exception.ResourceNotFoundException;
import com.example.FullStack.model.Employee;
import com.example.FullStack.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    @Autowired
    private  EmployeeRepo employeeRepo;
    //get all employee
    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return employeeRepo.findAll();
    }
    //create employee
    @PostMapping("/addEmployee")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepo.save(employee);
    }
    //get employee by  id
    @GetMapping("/employee/{id}")
    public ResponseEntity< Employee> getEmployeeById(@PathVariable Long id){
        Employee employee=employeeRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found with id: "+id));
        return ResponseEntity.ok(employee);
    }

    //update employee
    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id , @RequestBody Employee employeeDetails){
        Employee employee=employeeRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found with id: "+id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());

        Employee updatedEmployee=employeeRepo.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    //Delete Employee
    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee=employeeRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found with id: "+id));

        employeeRepo.delete(employee); // no response from delete i will make one
        Map<String,Boolean> response=new HashMap<>();
        response.put("Deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
