package com.studentmanagement.service;

import com.studentmanagement.exception.DuplicateEntityException;
import com.studentmanagement.exception.InvalidDataException;
import com.studentmanagement.model.Advisor;
import com.studentmanagement.repository.DataStore;
import com.studentmanagement.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdvisorService {

    private final DataStore dataStore = DataStore.getInstance();

    public List<Advisor> getAllAdvisors() {
        return new ArrayList<>(dataStore.getAdvisors());
    }

    public void addAdvisor(Advisor advisor) throws InvalidDataException, DuplicateEntityException {
        validateAdvisor(advisor);
        if (existsById(advisor.getAdvisorId())) {
            throw new DuplicateEntityException("Mã Cố Vấn '" + advisor.getAdvisorId() + "' đã tồn tại!");
        }
        dataStore.getAdvisors().add(advisor);
        dataStore.saveAdvisors();
    }

    public void updateAdvisor(Advisor advisor) throws InvalidDataException {
        validateAdvisor(advisor);
        List<Advisor> list = dataStore.getAdvisors();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getAdvisorId().equalsIgnoreCase(advisor.getAdvisorId())) {
                list.set(i, advisor);
                dataStore.saveAdvisors();
                return;
            }
        }
        throw new InvalidDataException("Không tìm thấy Cố Vấn có mã: " + advisor.getAdvisorId());
    }

    public void deleteAdvisor(String advisorId) throws InvalidDataException {
        boolean removed = dataStore.getAdvisors().removeIf(a -> a.getAdvisorId().equalsIgnoreCase(advisorId));
        if (!removed) {
            throw new InvalidDataException("Không tìm thấy Cố Vấn có mã: " + advisorId);
        }
        dataStore.saveAdvisors();
    }

    public List<Advisor> searchAdvisors(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getAllAdvisors();
        String k = keyword.trim().toLowerCase();
        return getAllAdvisors().stream()
                .filter(a -> a.getAdvisorId().toLowerCase().contains(k) || a.getFullName().toLowerCase().contains(k) || a.getFacultyId().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public boolean existsById(String advisorId) {
        return dataStore.getAdvisors().stream().anyMatch(a -> a.getAdvisorId().equalsIgnoreCase(advisorId));
    }

    private void validateAdvisor(Advisor a) throws InvalidDataException {
        if (a == null) throw new InvalidDataException("Thông tin Cố Vấn không được rỗng!");
        ValidationUtil.validateNotEmpty(a.getAdvisorId(), "Mã Cố Vấn");
        ValidationUtil.validateNotEmpty(a.getFullName(), "Họ và Tên Cố Vấn");
    }
}
