package uz.gigalab.remonlineimport.commandline;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import uz.gigalab.remonlineimport.dto.RemOnlineData;
import uz.gigalab.remonlineimport.service.ImportService;
import uz.gigalab.remonlineimport.service.ParseService;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class Import implements CommandLineRunner {

    @Value("classpath:data/import-data.xls")
    private Resource resourceFile;

    private final ParseService parseService;
    private final ImportService importService;

    @Override
    public void run(String... args) throws Exception {
        List<RemOnlineData> rl = parseService.readData(resourceFile);
        importService.saveData(rl);
    }
}
