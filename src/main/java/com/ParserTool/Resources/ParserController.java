package com.ParserTool.Resources;

import com.ParserTool.Interfaces.ParsedDataRepository;
import com.ParserTool.Model.ServerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
public class ParserController {
    @Autowired
    private ParsedDataRepository parsedDataRepository;
    @GetMapping("/parsed-data/{machineId}")
    public List<ServerData> getParsedData(@PathVariable String machineId) {
        return parsedDataRepository.findByMachineId(machineId);
    }
}
