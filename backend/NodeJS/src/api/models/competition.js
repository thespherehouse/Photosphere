import mongoose from 'mongoose'
import { Utils } from '../helper'

const schema = new mongoose.Schema({
    owner: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    name: { type: String, required: true },
    startDate: { type: Date, required: true },
    endDate: { type: Date, required: true },
    hasEnded: { type: Boolean, default: false }
},
    {
        toObject: {
            transform: function (doc, ret) {
                delete ret.__v
            }
        },
        toJSON: {
            transform: function (doc, ret) {
                delete ret.__v
            }
        }
    }
)

schema.index({ 'group': 1 })
schema.index({ 'createdAt': -1 })

export default mongoose.model('Competition', schema)  
