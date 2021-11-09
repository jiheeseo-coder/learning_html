import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.*;

public class TwitterStatusListener implements StatusListener {
    private static final Logger KAFKA_LOG = LoggerFactory.getLogger(TwitterStatusListener.class);

    @Override
    public void onStatus(Status status) {
        JSONObject tweet = new JSONObject();
        tweet.put("user", status.getUser().getScreenName());
        tweet.put("name", status.getUser().getName());
        tweet.put("location", status.getUser().getLocation());
        tweet.put("text", status.getText());
        
        HashtagEntity[] hashTags = status.getHashtagEntities();
        System.out.println("# HASH TAGS #");
        JSONArray jsonHashTags = new JSONArray();
        for (HashtagEntity hashTag : hashTags){
            System.out.println(hashTag.getText());
            jsonHashTags.put(hashTag.getText());
        }
        tweet.put("hashtags", jsonHashTags);

        System.out.println("@ USER MENTIONS @");
        UserMentionEntity[] mentions = status.getUserMentionEntities();
        JSONArray jsonMentions = new JSONArray();
        for (UserMentionEntity mention: mentions){
            System.out.println(mention.getScreenName());
            jsonMentions.put(mention.getScreenName());
        }
        tweet.put("mentions", jsonMentions);

        URLEntity[] urls = status.getURLEntities();
        System.out.println("$ URLS $");
        JSONArray jsonUrls = new JSONArray();
        for (URLEntity url: urls){
            System.out.println(url.getExpandedURL());
            jsonUrls.put(url.getExpandedURL());
        }
        tweet.put("urls", jsonUrls);

        if(status.isRetweet()){
            JSONObject retweetUser = new JSONObject();
            retweetUser.put("user", status.getUser().getScreenName());
            retweetUser.put("name", status.getUser().getName());
            retweetUser.put("location", status.getUser().getLocation());
            tweet.put("retweetuser", retweetUser);
        }
        KAFKA_LOG.info(tweet.toString());
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int i) {
        System.out.println("Track Limitation Notice "+ i);
    }

    @Override
    public void onScrubGeo(long l, long l1) {

    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {

    }

    @Override
    public void onException(Exception e) {

    }
}
