import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;

public class HelloWorld {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {
        HelloWorld h = new HelloWorld();
        staticFiles.location("/public");

        port(getHerokuAssignedPort());

        get("/", (req, res) -> {
            return new ModelAndView(new HashMap<>(), "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/greet", (req, res) -> {
            String name = req.queryParams("firstname");
            String language = req.queryParams("language");

            h.getName(name, language);

            Map<String, String> dataMap = new HashMap<>();
         dataMap.put("name", h.getName(name, language));
            return new ModelAndView(dataMap, "hello.hbs");

        }, new HandlebarsTemplateEngine());

    }

    Map<String, Integer> greetedMap  = new HashMap<>();
    public String getName(String name, String language) {

        String greeting = "Invalid language";

        if(language.equals("eng")) {
            greeting = "Good Morning, " + name;
        } else if (language.equals("xhosa")) {
            greeting = "Molo, " + name;
        } else if (language.equals("afr")) {
            greeting = "Goeiemore, " + name;
        }
//        if(language == null && name == "") {
//            System.out.println("Invalid");
//        }
        return greeting;
    }

    void counter(String name) {
        if (greetedMap.containsKey(name)) {
            greetedMap.put(name, 1);
        } else {
            greetedMap.put(name, greetedMap.get(name) + 1);
        }
    }






}
