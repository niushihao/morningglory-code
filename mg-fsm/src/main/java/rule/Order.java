package rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author qianniu
 * @date 2021/1/6 9:17 上午
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String userId;
    private Integer age;
    //是否是新客
    private Boolean isNew;
    private LocalDate orderDate;
    private BigDecimal price;
}


