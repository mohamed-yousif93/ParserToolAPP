package com.ParserTool.Services;

import com.ParserTool.Interfaces.ParsedDataRepository;
import com.ParserTool.Model.ServerData;
import com.sun.management.OperatingSystemMXBean;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@EnableScheduling
public class ParserService {
    OperatingSystemMXBean osMxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    @Autowired
    private  ParsedDataRepository parsedDataRepository;
    @Scheduled(fixedRate = 5000) // Run every 5 seconds
    public void parseData() {
        // Retrieve machine ID
        String machineId = getMachineId();

        // Retrieve CPU and RAM usage data
        long  cpuUsage =  Math.round(getCpuUsage());
        long  ramUsage = (long)(getRamUsage());

        // Create new parsed data object
        ServerData parsedData = new ServerData();
        parsedData.setMachineId(machineId);
        parsedData.setCpuUsage(cpuUsage);
        parsedData.setRamUsage(ramUsage);
        parsedData.setTimestamp(LocalDateTime.now());
        parsedData.setFlag("Stable");
        // Check if CPU usage exceeds 90% or RAM usage exceeds 3.5 GBs
       if (cpuUsage > 90 || ramUsage > 3.5) { // 90% and 3.5GB
           parsedData.setFlag("Critical");
       }
       //Save the server Usage in database
        parsedDataRepository.save(parsedData);
    }

    // Get RamUsage
    private double getRamUsage() {
       return (osMxBean.getTotalMemorySize() - osMxBean.getFreeMemorySize()) / (1024.0 * 1024.0 * 1024.0); // convert to GB
    }
    // Get CpuUsage
    private double getCpuUsage() {
        return osMxBean.getCpuLoad() * 100.0; // %
    }
    //Get getMachineId
    private String getMachineId() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
