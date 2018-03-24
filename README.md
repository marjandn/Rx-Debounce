# Rx-Debounce
use debounce method in Rxjava to make your app server HAPPY   :D

some functions like search and check sth like username or sth like this that need to send request to server a lot in short time ,
can be destructive , and sometimes maybe crash app, for a lot request that app want to send,

so with magic [RxJava](https://github.com/ReactiveX/RxJava) and that rich operators, we can improve this functions.
one of this effective operator is [debounce](http://reactivex.io/documentation/operators/debounce.html) that is made for 
that problems that i said,

in this source , i use debounce operator for checking username field in database

**the server side api is**

```
public string checkUsername(string username)
        {
            bool u = db.Users.Any(x => x.User_Username == username);
            
            if(u)
            {
                return "valid";
            }
            else
            {
                return "invalid";
            }
        }
```

# Used
* [Retrofit](http://square.github.io/retrofit/)
* [Rxjava](https://github.com/ReactiveX/RxJava)
* [Asp.net MVC](https://www.asp.net/mvc)


