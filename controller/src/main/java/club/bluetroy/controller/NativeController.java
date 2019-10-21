package club.bluetroy.controller;

import club.bluetroy.crawler.ui.NativeUi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heyixin
 * Date: 2018-12-03
 * Time: 09:57
 */
@RestController
@RequestMapping("/native")
public class NativeController {
    @GetMapping("/dirChooser")
    public String dirChooser() {
        return NativeUi.getPath();
    }
}
