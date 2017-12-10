import uuid from 'uuid/v1'
import multer from 'multer'
import multerS3 from 'multer-s3'
import AWS from 'aws-sdk'
import { Response } from '../helper'

AWS.config.update({
    accessKeyId: ' AKIAIIDKJAN6AIORNXBQ',
    secretAccessKey: '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',
    region: 'us-west-1'
})
let s3 = new AWS.S3()

export function uploader(req, res, next) {
    let userId = req.user._id
    let filename = uuid() + '.jpg'
    let upload = multer({
        storage: multerS3({
            s3: s3,
            bucket: 'thespherehouse',
            acl: 'public-read',
            contentType: multerS3.AUTO_CONTENT_TYPE,
            key: (req, file, cb) => {
                cb(null, `${userId}/${filename}`)
            }
        })
    })
    upload.single('photo')(req, res, function (err) {

        if (err)
            return Response.sendError(res, Response.Errors.Internal)

        req.file.name = filename
        next()

    })
}
