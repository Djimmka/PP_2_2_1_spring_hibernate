package hiber.model;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name= "series")
    private int series;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Car(){}

    public Car(String model, int series){
        this.model = model;
        this. series = series;
    }

    public Car(String model, int series, User user){
        this(model,series);
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public int getSeries() {
        return series;
    }

    public String getModel() {
        return model;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
