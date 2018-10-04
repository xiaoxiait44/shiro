package com.shiro.realm;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
/**
 * 鑷畾涔塕ealm
 * @author PDD
 *
 */
public class CustomRealm extends  AuthorizingRealm {
	Map<String,String>  userMap = new HashMap<String,String>(16);
	{
		userMap.put("xxa", "95e7f080d93915662d9a2f486417e2ae");
		super.setName("customRole");
	}
     /**
               * 鎺堟潈
      */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//鑾峰彇鐢ㄦ埛鍚�
		String  userName = (String) principals.getPrimaryPrincipal();
		
		Set<String>  role = getuserNamebyRole(userName);
		Set<String>  permissios = getuserNamebypermissios(userName);
		SimpleAuthorizationInfo  simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		//璁剧疆瑙掕壊
		simpleAuthorizationInfo.setRoles(role);
		//璁剧疆鏉冮檺
		simpleAuthorizationInfo.setStringPermissions(permissios);
		return simpleAuthorizationInfo;
	}
	/**
	 * 妯℃嫙鏁版嵁搴撴煡璇㈡潈闄�
	 * @param userName
	 * @return
	 */
	private Set<String> getuserNamebypermissios(String userName) {
		Set<String> permissios = new HashSet<>();
		permissios.add("user:delete");
		permissios.add("user:select");
		return permissios;
	}
	/**
	 * 妯℃嫙鏁版嵁搴撴煡璇㈣鑹�
	 * @param userName
	 * @return
	 */
    private Set<String> getuserNamebyRole(String userName) {
		Set<String> role = new HashSet<>();
		role.add("user");
		role.add("admin");
		return role;
	}
	/**
            * 璁よ瘉
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//从主体获取用户名
		String userName = (String)token.getPrincipal();
		if(userName == null) {
			return null;
		}
		String password =	userNameBypassWord(userName);
		SimpleAuthenticationInfo   author = new SimpleAuthenticationInfo("xxa",password,"customRole");
		//盐值加密
		author.setCredentialsSalt(ByteSource.Util.bytes("mark"));
		return author;
	}
    /**
              * 妯℃嫙鏁版嵁鏌ヨ鐢ㄦ埛瀵嗙爜
     */
	private String userNameBypassWord(String userName) {
		
		return userMap.get(userName);
	}
	
	public static void main(String[] args) {
		Md5Hash md5 = new Md5Hash("1234","mark");
		System.out.println(md5.toString());

	}

}
