# Config

配置读取流程:

- 读取文件
- 选择对应的解析器, 如 snakeyaml-engine
- 将解析结果转化为 ConfigSection
- 如果有模板, 则对照模板验证转化完的 ConfigSection
- - 














## Message
### Pieces
#### Color
- &a, §c... -----------------------Simple color
- {#121212} -----------------------Hex color
- {rgb:123,4,32} ------------------RGB color
- {#~<#222210,#0f2340>},
- {#~<#222210,#0f2340>(Message)} --Advance color
#### Math

#### Condition
- {?(条件表达式)}
#### Hover
- {hover:(Normal message);(Hover message);(Click action)}
#### ShowedItem
#### ToPath
{path@(路径类型):()\(路径)}

比如: {path@global_config:Auspice\global.debug}


## Color




# 法术工艺


