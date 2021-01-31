<template>
    <div id="upload">
        <div class="container">
            <!--UPLOAD-->
            <form enctype="multipart/form-data" novalidate v-if="true || isInitial || isSaving">
                <!--<h1>Upload images</h1>-->
                <div class="dropbox">
                    <input type="file" multiple :name="uploadFieldName" :disabled="isSaving"
                           @change="filesChange($event); fileCount = $event.target.files.length"
                           accept="application/xml" class="input-file">
                    <p v-if="isInitial">
                        Drag your file(s) here to begin<br> or click to browse
                    </p>
                    <p v-if="isSaving">
                        Uploading {{ fileCount }} files...
                    </p>
                    <!--SUCCESS-->
                    <div v-if="isSuccess">
                        <h2>Uploaded {{ uploadedFiles.length }} file(s) successfully.</h2>
                    </div>
                    <!--FAILED-->
                    <div v-if="isFailed">
                        <h2>Uploaded failed.</h2>
                        <p>
                            <a href="javascript:void(0)" @click="reset()">Try again</a>
                        </p>
                        <pre>{{ uploadError }}</pre>
                    </div>
                </div>
            </form>

        </div>
    </div>
</template>

<script>
  // swap as you need
  import { upload } from './file-upload.fake.service'; // fake service
  // import { upload } from './file-upload.service';   // real service
  import { wait } from './utils';

  const STATUS_INITIAL = 0, STATUS_SAVING = 1, STATUS_SUCCESS = 2, STATUS_FAILED = 3;

  export default {
    name: 'upload',
    data() {
      return {
        uploadedFiles: [],
        uploadError: null,
        currentStatus: null,
        uploadFieldName: 'graphs'
      }
    },
    computed: {
      isInitial() {
        return this.currentStatus === STATUS_INITIAL;
      },
      isSaving() {
        return this.currentStatus === STATUS_SAVING;
      },
      isSuccess() {
        return this.currentStatus === STATUS_SUCCESS;
      },
      isFailed() {
        return this.currentStatus === STATUS_FAILED;
      }
    },
    methods: {
      reset() {
        // reset form to initial state
        this.currentStatus = STATUS_INITIAL;
        this.uploadedFiles = [];
        this.uploadError = null;
      },
      save(formData) {
        // upload data to the server
        this.currentStatus = STATUS_SAVING;

        upload(formData)
          //.then(wait(1500)) // DEV ONLY: wait for 1.5s
          .then(x => {
            this.uploadedFiles = [].concat(x);
            this.currentStatus = STATUS_SUCCESS;
            this.$emit("xmlGraph", x[0].url);
          })
          .catch(err => {
            this.uploadError = err.response;
            this.currentStatus = STATUS_FAILED;
          });
      },
      filesChange($event) {
        let fieldName = $event.target.name;
        let fileList = $event.target.files;
        // handle file changes
        const formData = new FormData();

        if (!fileList.length) return;

        // append the files to FormData
        Array
          .from(Array(fileList.length).keys())
          .map(x => {
            formData.append(fieldName, fileList[ x ], fileList[ x ].name);
          });

        // save it
        this.save(formData);
      }
    },
    mounted() {
      this.reset();
    },
  }

</script>

<style scoped>
    .dropbox {
        outline: 2px dashed grey;
        /* the dash box */
        outline-offset: -10px;
        background: lightcyan;
        color: dimgray;
        padding: 5px 5px;
        /*min-height: 50px;*/
        height: 85px;
        /* minimum height */
        position: relative;
        cursor: pointer;
    }

    .input-file {
        opacity: 0;
        /* invisible but it's there! */
        width: 100%;
        height: 85px;
        position: absolute;
        cursor: pointer;
    }

    .dropbox:hover {
        background: lightblue;
        /* when mouse over to the drop zone, change color */
    }

    .dropbox p {
        font-size: 1.2em;
        text-align: center;
        padding: 10px 0;
    }
</style>