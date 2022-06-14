import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Data
@RequiredArgsConstructor
@Value


public class RegistrationDto {

    private final String  login;
    private final String  password;
    private final String status;

}
