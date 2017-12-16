import mongoose from 'mongoose'
import { Utils } from '../helper'

const schema = new mongoose.Schema({
    name: { type: String, required: true, unique: true }
}, {
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

schema.statics.createCategory = function (name, cb) {
    return this.create({ name })
}

schema.statics.getCategories = function (cb) {
    return this.find({}, cb)
}

schema.statics.editCategoryName = function (categoryId, name, cb) {
    return this.findByIdAndUpdate(categoryId, { name }, cb)
}

schema.statics.deleteCategory = function (categoryId, cb) {
    return this.findOneAndRemove({ _id: categoryId }, cb)
}

module.exports = mongoose.model('Category', schema)
