package model;

import exception.ValidationException;
import javax.validation.*;
import javax.validation.constraints.Min;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Plateau {
    @Min(value = 1, message = "Width should be greater than 1")
    private int width;

    @Min(value = 1, message = "Height should be greater than 1")
    private int height;

    private Dimension size;

    private List<Sample> samples;

    public Plateau (int width, int height) throws ValidationException {
        this.width = width;
        this.height = height;
        validate();
        this.size = new Dimension(width, height);
        samples = new ArrayList<>();
    }
    public Dimension getSize() {
        return size;
    }

    public void validate() throws ValidationException{
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Plateau>> violations = validator.validate(this);
        for (ConstraintViolation<Plateau> violation : violations) {
            throw new ValidationException(violation.getMessage());
        }
    }


    public void generateSample() {
        samples.add(new Sample(new Point(1,1)));
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public boolean hasSample(Point location){
        boolean hasSample = false;
        for (Sample sample: samples){
            if (location.equals(sample.getLocation())){
                hasSample = true;
                break;
            }
        }
        return hasSample;
    }
}