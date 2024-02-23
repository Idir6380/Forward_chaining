import java.util.ArrayList;

public class ForwardChaining {
    public Rule choose_rule( ArrayList<Rule> rules, ArrayList<String> fact_base) {
        for (Rule rule : rules){

            if (fact_base.containsAll(rule.premisses)){
                return rule;
            }
        }
        return null;
    }
    public boolean forward_chaining(ArrayList<String> goal, Rule[] rule_base, ArrayList<String> fact_base){
        boolean allGoalsSatisfied = true;
        for (String g : goal) {
            if (!fact_base.contains(g)) {
                allGoalsSatisfied = false;
                break;
            }
        }
        if (allGoalsSatisfied){
            return true;
        }
        else {
            //rules no triggered
            ArrayList<Rule> RND = new ArrayList<Rule>();
            for (Rule rule : rule_base) {
                RND.add(rule);
            }

            //rule to consider
            ArrayList<Rule> RTC = new ArrayList<Rule>(RND);

            boolean result = false;
            Rule rule;
            while (!RTC.isEmpty() && !result){

                rule = choose_rule(RND, fact_base);
                if (rule == null){
                    break;
                }
                System.out.println("choosed rule number:"+rule.index);
                RTC.remove(rule);
                //premisses in BF
                boolean PIB = true;
                for (String pms : rule.getPremisses()){
                    if (!fact_base.contains(pms)){
                        PIB = false;
                        break;
                    }
                }
                if (PIB){
                    ArrayList<String> conclusion = rule.getConclusion();

                    fact_base.addAll(conclusion);
                    RND.remove(rule);
                    RTC.remove(rule);

                    if (fact_base.containsAll(goal)){
                        result = true;
                    }
                }
            }
            return result;
        }
    }
}
