package net.chaeyk.bigmem;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/alloc/{threads}/{size}/{count}")
    public String alloc(@PathVariable int threads,
                        @PathVariable int size,
                        @PathVariable int count) {
        var thread = new AllocTest(threads, size, count);
        thread.start();
        return "OK";
    }
}
