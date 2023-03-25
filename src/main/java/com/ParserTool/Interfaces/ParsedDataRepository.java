package com.ParserTool.Interfaces;
import com.ParserTool.Model.ServerData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParsedDataRepository extends JpaRepository<ServerData, Long> {
   List<ServerData> findByMachineId(String machineId);
}
