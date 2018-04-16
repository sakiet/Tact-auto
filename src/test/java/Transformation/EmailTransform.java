package Transformation;

import cucumber.api.Transformer;

public class EmailTransform extends Transformer<String> {

    @Override
    public String transform(String userName){
        return userName.concat("@tact.ai");
    }
}
