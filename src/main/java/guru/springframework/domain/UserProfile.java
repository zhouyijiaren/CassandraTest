package guru.springframework.domain;

import com.datastax.driver.core.DataType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by xiang.zhou on 1/10/17.
 */
@Table("user_profile")
public class UserProfile implements Serializable{

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    private int userIdHash;
    private String userId;
    private String profile;

    public int getUserIdHash() {
        return userIdHash;
    }

    public void setUserIdHash(int userIdHash) {
        this.userIdHash = userIdHash;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}
