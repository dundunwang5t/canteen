<template>
  <div class="card">
    <el-form :model="data.user" label-width="100px" style="padding-right: 50px;width: 600px" >
      <el-form-item label="头像">
        <el-upload :show-file-list="false" class="avatar-uploader" action="http://localhost:9090/files/upload" :on-success="handleFileUpload">
          <img v-if="data.user.avatar" :src="data.user.avatar" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="账号">
        <el-input disabled v-model="data.user.username" autocomplete="off" />
      </el-form-item>
      <el-form-item label="名称">
        <el-input v-model="data.user.name" autocomplete="off" />
      </el-form-item>

      <el-form-item label="性别" v-if="data.user.role === 'USER'">
        <el-radio-group v-model="data.user.sex">
          <el-radio label="男"></el-radio>
          <el-radio label="女"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="手机"  v-if="data.user.role === 'USER'">
        <el-input v-model="data.user.phone" autocomplete="off" />
      </el-form-item>
      <el-form-item label="余额" v-if="data.user.role === 'USER'">
        <el-input readonly v-model="data.user.account" autocomplete="off" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import {reactive} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";

// 定义事件名称
const emit = defineEmits(["updateUser"])
// 在子组件中设置发射的数据和方法
emit('updateUser')
// 定义数据
const data = reactive({
  user:JSON.parse(localStorage.getItem('canteen-user') || '{}')
})
// 定义事件方法
// const updateUser = () => {
//   data.user = JSON.parse(localStorage.getItem('canteen-user') || '{}')
// }
// 保存数据
const save=()=>{
if (data.user.role==="ADMIN"){
  request.put('/admin/update',data.user).then(res=>{
    if (res.code==='200'){
      ElMessage.success('更新成功')
    }else{
      ElMessage.error(res.msg)
    }
  })
}else {
  request.put('/user/update',data.user).then(res=>{
    if (res.code==='200'){
      ElMessage.success('更新成功')
    }else{
      ElMessage.error(res.msg)
    }
  })
}
//把更新后的用户信息存储到本地缓存
  localStorage.setItem('canteen-user',JSON.stringify(data.user))

}
// 文件上传
const handleFileUpload=(file)=>{
  data.user.avatar = file.data
}
</script>

<style scoped>
.avatar-uploader .avatar {
  width: 120px;
  height: 120px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  text-align: center;
}
</style>
