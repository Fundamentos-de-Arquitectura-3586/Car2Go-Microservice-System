
@Service
public class PaymentService{
    private final WebClient webClient;

    public PaymentService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public Vehicle getVehicleById(Long vehicleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getCredentials().toString();

        return webClient.get()
                .uri("/vehicle-service/api/v1/vehicle/{id}", vehicleId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(Vehicle.class)
                .block();
    }

    public User getUserById(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getCredentials().toString();

        return webClient.get()
                .uri("/iam-service/api/v1/users/{id}", userId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }
}