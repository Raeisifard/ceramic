function upload(formData) {
    const photos = formData.getAll('graphs');
    const promises = photos.map((x) => getFile(x)
        .then(xml => ({
            id: xml,
            originalName: x.name,
            fileName: x.name,
            url: xml
        })));
    return Promise.all(promises);
}

function getFile(file) {
    return new Promise((resolve, reject) => {
        const fReader = new FileReader();
        //const img = document.createElement('img');

        fReader.onload = () => {
            //img.src = fReader.result;
            resolve(fReader.result);
        };

        fReader.readAsText(file);
    })
}

function getBase64Image(img) {
    const canvas = document.createElement('canvas');
    canvas.width = img.width;
    canvas.height = img.height;

    const ctx = canvas.getContext('2d');
    ctx.drawImage(img, 0, 0);
    const dataURL = img.src;
    return dataURL;
}

export { upload }

