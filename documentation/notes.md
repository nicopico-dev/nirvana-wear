# HTTP analysis

Use https://github.com/meeech/nirv as a base

## General

### Error handling
All errors return a 200 response with an error payload. The error code can be retrieved with
```js
results[0].error.code       // Int
results[0].error.message    // String
```

## Authentication
https://github.com/meeech/nirv/blob/master/lib/nirvanahq/auth.rb

```shell
curl -sX POST 'https://api.nirvanahq.com/?api=rest' -d "method=auth.new&u=#{@username}&p=#{@password}
```

Password must be MD5 encoded. Response is in JSON format

### Success
Auth token is in `results[0].auth.token`

### Errors
| Code | Message               |
|------|-----------------------|
| 98   | Invalid Login Details |

## Retrieve tasks
https://github.com/meeech/nirv/blob/master/lib/nirvanahq/task.rb

```shell
curl -sX GET 'https://api.nirvanahq.com/?api=rest&appid=gem&authtoken=#{@token}&method=everything&since=0'
```

### Success
All the data is dumped in a big JSON payload with a structure like

```json
{
  "request": {},
  "results": [
    {
      "user": {}
    },
    {
      "pref": {}
    },
    {}
  ]
}
```

the `results` table contains N objects with a single key giving the type of the value.
Types are:
- user
- pref
- tag
- task

TODO Make class diagram

Deleted objects are also present with the `deleted` property set to the timestamp of the deletion
```json
[
  {
    "tag": {
      "key": "Deleted tag",
      "deleted": "1638207131",
      "_deleted": "1638207131"
    }
  }
]
```

### Parameters
#### `since`
Retrieve changes from a point in time.

The maximum value can be retrieved from the authentication response with `result[0].auth.user.lastwritebyuseron`.
Using a value bigger than the maximum timestamp will return only `user` and `pref` objects.

:alarm: WARN: I do not understand yet how the filtering is done. Using the maximum timestamp seems to return some of the
most recent tasks, however none of the tags are present (probably because they did not change for a while)

#### `method`
Retrieve only a single type of objects

Possible values:
- `everything`
- `user`
- `tasks`
- `tags`
- `prefs`

It is possible to combine multiple values (ex: `method=user&method=prefs`)

Using an incorrect value will return a 404 error

### Errors
| Code | Message           |
|------|-------------------|
| 2    | Invalid Authtoken |
