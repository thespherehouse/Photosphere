import uniqId from 'uniqid'
import multer from 'multer'
import multerS3 from 'multer-s3'
import AWS from 'aws-sdk'
import { Response, Errors } from '../helper'
import * as Config from '../../config'

AWS.config.update({
    accessKeyId: Config.AWS_ACCESS_KEY_ID,
    secretAccessKey: Config.AWS_SECRET_ACCESS_KEY,
    region: Config.AWS_REGION
})

const s3 = new AWS.S3()

export default function () {

    return (req, res, next) => {
        const userId = req.user._id
        const upload = multer({
            fileFilter: (req, file, cb) => {
                let allow = true

                // Dont allow if file is not PNG or JPEG
                if (file === null || (file.mimetype !== 'image/png' && file.mimetype !== 'image/jpeg'))
                    allow = false

                // Dont allow more than 10MB
                if (file.size > 10485760)
                    allow = false

                cb(null, allow)
            },
            storage: multerS3({
                s3,
                bucket: 'thespherehouse',
                acl: 'public-read',
                contentType: multerS3.AUTO_CONTENT_TYPE,
                key: (req, file, cb) => {
                    cb(null, `${userId}/${uniqId('image-')}`)
                }
            })
        })
        upload.single('photo')(req, res, function (err) {

            if (err)
                return Response.sendError(res, Errors.Incomplete)

            next()
        })
    }

}
