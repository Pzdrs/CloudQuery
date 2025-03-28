package cz.pycrs.cloudquery.repository;

import cz.pycrs.cloudquery.entity.OWMResponse;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OWMResponseRepository extends MongoRepository<OWMResponse, ObjectId> {
}
