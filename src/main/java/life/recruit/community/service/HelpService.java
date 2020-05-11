package life.recruit.community.service;

import life.recruit.community.mapper.HelpMapper;
import life.recruit.community.model.Help;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelpService {

    @Autowired
    private HelpMapper helpMapper;

    public void insert(Help help){
        helpMapper.insert(help);
    }

    public void updateAccomplish(Integer articleId) {
        helpMapper.updateAccomplish(articleId);
    }
}
