

Sentinel控制台
======

# 流控规则
http://localhost:8081/#/dashboard/flow/sentinel-demo

## 流控规则列表
请求网址: http://localhost:8081//v1/flow/rules?app=sentinel-demo&ip=172.16.22.50&port=8720
请求方法: GET
请求载荷
```
app=sentinel-demo&ip=172.16.22.50&port=8720
```
响应
```json
{
    "success": true,
    "code": 0,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "app": "sentinel-demo",
            "ip": "172.16.22.50",
            "port": 8720,
            "limitApp": "default",
            "resource": "sayHello",
            "grade": 1,
            "count": 100.0,
            "strategy": 0,
            "refResource": null,
            "controlBehavior": 0,
            "warmUpPeriodSec": null,
            "maxQueueingTimeMs": null,
            "clusterMode": false,
            "clusterConfig": {
                "flowId": null,
                "thresholdType": 0,
                "fallbackToLocalWhenFail": true,
                "strategy": 0,
                "sampleCount": 10,
                "windowIntervalMs": 1000,
                "resourceTimeout": 2000,
                "resourceTimeoutStrategy": 0,
                "acquireRefuseStrategy": 0,
                "clientOfflineTime": 2000
            },
            "gmtCreate": "2024-09-13T08:08:54.646+00:00",
            "gmtModified": "2024-09-13T08:08:54.646+00:00"
        },
        {
            "id": 2,
            "app": "sentinel-demo",
            "ip": "172.16.22.50",
            "port": 8720,
            "limitApp": "default",
            "resource": "TestResource",
            "grade": 1,
            "count": 30.0,
            "strategy": 0,
            "refResource": null,
            "controlBehavior": 0,
            "warmUpPeriodSec": null,
            "maxQueueingTimeMs": null,
            "clusterMode": false,
            "clusterConfig": {
                "flowId": null,
                "thresholdType": 0,
                "fallbackToLocalWhenFail": true,
                "strategy": 0,
                "sampleCount": 10,
                "windowIntervalMs": 1000,
                "resourceTimeout": 2000,
                "resourceTimeoutStrategy": 0,
                "acquireRefuseStrategy": 0,
                "clientOfflineTime": 2000
            },
            "gmtCreate": "2024-09-13T08:15:27.685+00:00",
            "gmtModified": "2024-09-13T08:15:27.685+00:00"
        }
    ]
}
```

## 新增流控规则
请求网址: http://localhost:8081//v1/flow/rule
请求方法: POST
请求载荷
```json
{
  "grade": 1,
  "strategy": 0,
  "controlBehavior": 0,
  "app": "sentinel-demo",
  "ip": "172.16.22.50",
  "port": "8720",
  "limitApp": "default",
  "clusterMode": false,
  "clusterConfig": {
    "thresholdType": 0
  },
  "resource": "testResourceName",
  "count": 60
}
```
响应
```json
{
    "success": true,
    "code": 0,
    "msg": "success",
    "data": {
        "id": 3,
        "app": "sentinel-demo",
        "ip": "172.16.22.50",
        "port": 8720,
        "limitApp": "default",
        "resource": "testResourceName",
        "grade": 1,
        "count": 60.0,
        "strategy": 0,
        "refResource": null,
        "controlBehavior": 0,
        "warmUpPeriodSec": null,
        "maxQueueingTimeMs": null,
        "clusterMode": false,
        "clusterConfig": {
            "flowId": null,
            "thresholdType": 0,
            "fallbackToLocalWhenFail": true,
            "strategy": 0,
            "sampleCount": 10,
            "windowIntervalMs": 1000,
            "resourceTimeout": 2000,
            "resourceTimeoutStrategy": 0,
            "acquireRefuseStrategy": 0,
            "clientOfflineTime": 2000
        },
        "gmtCreate": "2024-09-13T08:27:36.639+00:00",
        "gmtModified": "2024-09-13T08:27:36.639+00:00"
    }
}
```

## 编辑流控规则
请求网址: http://localhost:8081//v1/flow/save.json?controlBehavior=0&count=600&grade=1&id=3&limitApp=default&resource=testResourceName&strategy=0
请求方法: PUT
请求载荷
```
controlBehavior=0&count=600&grade=1&id=3&limitApp=default&resource=testResourceName&strategy=0
```
响应
```json
{
    "success": true,
    "code": 0,
    "msg": "success",
    "data": {
        "id": 3,
        "app": "sentinel-demo",
        "ip": "172.16.22.50",
        "port": 8720,
        "limitApp": "default",
        "resource": "testResourceName",
        "grade": 1,
        "count": 600.0,
        "strategy": 0,
        "refResource": null,
        "controlBehavior": 0,
        "warmUpPeriodSec": null,
        "maxQueueingTimeMs": null,
        "clusterMode": false,
        "clusterConfig": {
            "flowId": null,
            "thresholdType": 0,
            "fallbackToLocalWhenFail": true,
            "strategy": 0,
            "sampleCount": 10,
            "windowIntervalMs": 1000,
            "resourceTimeout": 2000,
            "resourceTimeoutStrategy": 0,
            "acquireRefuseStrategy": 0,
            "clientOfflineTime": 2000
        },
        "gmtCreate": "2024-09-13T08:27:36.639+00:00",
        "gmtModified": "2024-09-13T08:33:41.082+00:00"
    }
}
```

## 删除流控规则
请求网址: http://localhost:8081//v1/flow/delete.json?app=sentinel-demo&id=3
请求方法: DELETE
请求载荷
```
app=sentinel-demo&id=3
```
响应
```json
{
  "success": true,
  "code": 0,
  "msg": "success",
  "data": 3
}
```

