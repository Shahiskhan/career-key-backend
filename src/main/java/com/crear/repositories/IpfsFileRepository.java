package com.crear.repositories;

import com.crear.entities.IpfsFile;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpfsFileRepository extends JpaRepository<IpfsFile, UUID> {
}
