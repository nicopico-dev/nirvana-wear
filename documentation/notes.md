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

## Models

Each property is doubled with an underscore prefix. Example:

```json
{
  "deleted": 0,
  "_deleted": 1638207131
}
```

I think `_property` is the timestamp of the latest change to `property`.
`_property` are omitted in the following examples for brevity

### User

```json
{
  "id": "123456",
  "n1sunset": "1648047222",
  "servicelevel": "pro",
  "maxareas": "99999",
  "maxprojects": "99999",
  "maxreflists": "99999",
  "maxlogbook": "90",
  "version": "3",
  "username": "",
  "password": "PASSWORD_MD5",
  "gmtoffset": "1",
  "firstname": "",
  "lastname": "",
  "emailaddress": "someone@mail.com",
  "inmailaddress": "78c11421f7",
  "pokememon": "0",
  "pokemetue": "0",
  "pokemewed": "0",
  "pokemethu": "0",
  "pokemefri": "0",
  "pokemesat": "0",
  "pokemesun": "0",
  "lastwritebyuseron": "1671815565",
  "lastactivityon": "0",
  "lastactivityip": "111.222.333.444",
  "experimentalfeatures": "0",
  "createdon": "1638047111",
  "lastpaymentdate": "0",
  "nextpaymentdate": "0",
  "intrialperiod": "0",
  "trialenddate": "0",
  "incompperiod": "0",
  "compenddate": "0",
  "ingraceperiod": "0",
  "graceenddate": "0",
  "issuspended": "0",
  "feedkey": "",
  "remindersviaemail": "1"
}
```

### Pref

```json
{
  "key": "UISortReflists",
  "value": "alpha",
  "deleted": "0"
}
```

| Key                  | Type | Description |
|----------------------|------|-------------|
| UIBFocusGroupBy      | enum | `state`     |
| UIBNextGroupBy       | enum | `parentId`  |
| UIEnableLater        | bool |             |
| UIDefaultProjectType | int  |             |
| UIEnableRapidEntry   | bool |             |
| UIBCollapseContexts  | bool |             |
| UIDateWeekStartsOn   | int  |             |
| UIBTaskDetails       | bool |             |
| UIBCollapseProjects  | bool |             |
| UIBCollapseCleanup   | bool |             |
| UIBCollapseReference | bool |             |
| UIDateLocale         | str  | Locale      |
| UIMTaskShowNotes     | bool |             |
| UITimeFormat         | str  | `24h`       |
| UIMCollapseNotes     | bool |             |
| UIAreaAssignment     | bool |             |
| UIAreaFiltering      | enum | `inclusive` |
| UIBCollapseActions   | bool |             |
| UIBAppearance        | enum | `light`     |
| UIBCollapseCollect   | bool |             |
| UIBCollapseCollect   | str  | empty str   |
| UIMTaskShowEffort    | bool |             |
| UISortReflists       | enum | `alpha`     |

**Notes**

- bool: 0 / 1
- enum: str with a defined set of possible values

2 keys for `UIBCollapseCollect` :thinking:

### Tag

There are several types of tags

#### Area

```json
{
  "key": "personal",
  "type": "1",
  "email": "",
  "color": "blue",
  "meta": "",
  "deleted": "0"
}
```

#### Contact

```json
{
  "key": "Someone",
  "type": "2",
  "email": "",
  "color": "",
  "meta": "",
  "deleted": "0"
}
```

#### Label

```json
{
  "key": "important 🚨",
  "type": "0",
  "email": "",
  "color": "red",
  "meta": "",
  "deleted": "0"
}
```

#### Deleted tag

Deleted tag have no properties except their name

```json
{
  "key": "🚨",
  "deleted": "1666077129"
}
```

### Task

There are several type of tasks

#### Reference list

```json
{
  "id": "54F6782D-4200-4DC1-F5E4-5153BC960823",
  "type": "3",
  "ps": "0",
  "state": "10",
  "parentid": "",
  "seq": "4",
  "seqt": "0",
  "seqp": "0",
  "name": "📚 Bandes dessinées",
  "tags": ",personal,",
  "etime": "0",
  "energy": "0",
  "waitingfor": "",
  "startdate": "",
  "duedate": "",
  "reminder": "",
  "recurring": "",
  "note": "",
  "completed": "0",
  "cancelled": "0",
  "ucreated": "0",
  "deleted": "0",
  "updated": "1666855782"
}
```

#### Reference item

```json
{
  "id": "C4G3R34B-0337-4236-DE72-DB030AA8CF63",
  "type": "2",
  "ps": "0",
  "state": "10",
  "parentid": "54F6782D-4200-4DC1-F5E4-5153BC960823",
  "seq": "1",
  "seqt": "0",
  "seqp": "0",
  "name": "Hellboy",
  "tags": ",",
  "etime": "0",
  "energy": "0",
  "waitingfor": "",
  "startdate": "",
  "duedate": "",
  "reminder": "",
  "recurring": "",
  "note": "1-15",
  "completed": "0",
  "cancelled": "0",
  "ucreated": "0",
  "deleted": "0",
  "updated": "1671285029"
}
```

#### Project

```json
{
  "id": "435D34E7-D1CA-353F-A84A-04581224F8DE",
  "type": "1",
  "ps": "1",
  "state": "4",
  "parentid": "",
  "seq": "2",
  "seqt": "0",
  "seqp": "0",
  "name": "Débarrasser le garage",
  "tags": ",personal,",
  "etime": "0",
  "energy": "0",
  "waitingfor": "",
  "startdate": "",
  "duedate": "",
  "reminder": "",
  "recurring": "",
  "note": "Collecte solidaire 2022: -> Place Abbé Pierre\n400m\nPente",
  "completed": "0",
  "cancelled": "0",
  "ucreated": "0",
  "deleted": "0",
  "updated": "1669486206"
}
```

#### Standard task

```json
{
  "id": "ACEA1C5A-DF38-43D4-A2E3-EDCFEBFC5FB5",
  "type": "0",
  "ps": "0",
  "state": "4",
  "parentid": "435D34E7-D1CA-353F-A84A-04581224F8DE",
  "seq": "1666720105",
  "seqt": "0",
  "seqp": "2",
  "name": "Jeter le frigo",
  "tags": ",à-la-maison 🏠,",
  "etime": "0",
  "energy": "3",
  "waitingfor": "",
  "startdate": "",
  "duedate": "",
  "reminder": "",
  "recurring": "",
  "note": "",
  "completed": "0",
  "cancelled": "0",
  "ucreated": "0",
  "deleted": "0",
  "updated": "1666720105"
}
```

#### Deleted tasks

Deleted tasks only have a state and an ID

```json
{
  "id": "A4325CA5-0EEC-3F42-A29B-19810DE8A2B8",
  "state": "8",
  "deleted": "1638131245"
}
```

### Task properties

| Key        | Type  | Description                     |
|------------|-------|---------------------------------|
| id         | UUID  |                                 |
| type       | int   |                                 |
| ps         |       |                                 |
| state      |       |                                 |
| parentid   | UUID? | ID of project or reference list |
| seq        |       |                                 |
| seqt       |       |                                 |
| seqp       |       |                                 |
| name       |       |                                 |
| tags       |       |                                 |
| etime      |       |                                 |
| energy     |       |                                 |
| waitingfor | UUID? | ID of waiting contact           |
| startdate  |       |                                 |
| duedate    |       |                                 |
| reminder   |       |                                 |
| recurring  |       |                                 |
| note       |       |                                 |
| completed  |       |                                 |
| cancelled  |       |                                 |
| ucreated   |       |                                 |
| deleted    |       |                                 |
| updated    |       |                                 |
