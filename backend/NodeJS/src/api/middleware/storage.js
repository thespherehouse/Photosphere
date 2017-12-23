import uniqId from 'uniqid'
import multer from 'multer'
import multerS3 from 'multer-s3-transform'
import AWS from 'aws-sdk'
import { Response, Errors } from '../helper'
import * as Config from '../../config'
import sharp from 'sharp'

AWS.config.update({
    accessKeyId: Config.AWS_ACCESS_KEY_ID,
    secretAccessKey: Config.AWS_SECRET_ACCESS_KEY,
    region: Config.AWS_REGION
})

const s3 = new AWS.S3()

export default (field) => {

    return (req, res, next) => {
        const id = uniqId()
        const userId = req.user._id
        const upload = multer({
            fileFilter: (req, file, cb) => {
                console.log('Hello' + JSON.stringify(req.body))
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
                bucket: Config.Image.BUCKET,
                acl: 'public-read',
                contentType: multerS3.AUTO_CONTENT_TYPE,
                shouldTransform: (req, file, cb) => {
                    cb(null, true)
                },
                transforms: [{
                    id: 'original',
                    key: (req, file, cb) => {
                        cb(null, `${userId}/${id}/${Config.Image.ORIG_NAME}`)
                    },
                    transform: (req, file, cb) => {
                        const sharpStream = sharp()
                        const resizeStream = sharpStream.clone().resize(Config.Image.MAX_SIZE, Config.Image.MAX_SIZE).max().toFormat(Config.Image.ORIG_FORMAT)
                        const metadataStream = sharpStream.clone().metadata().then((metadata) => {
                            req.imageAspectRatio = metadata.width / metadata.height
                        })

                        cb(null, sharpStream)
                    }
                }, {
                    id: 'thumbnail',
                    key: (req, file, cb) => {
                        cb(null, `${userId}/${id}/${Config.Image.THUMB_NAME}`)
                    },
                    transform: (req, file, cb) => {
                        cb(null, sharp().resize(Config.Image.THUMB_SIZE, Config.Image.THUMB_SIZE)
                            .max().toFormat(Config.Image.THUMB_FORMAT))
                    }
                }]
            })
        })
        upload.single(field)(req, res, function (err) {

            if (err)
                return Response.sendError(res, Errors.Incomplete)

            req.file.key = `${userId}/${id}`
            next()
        })
    }

}
